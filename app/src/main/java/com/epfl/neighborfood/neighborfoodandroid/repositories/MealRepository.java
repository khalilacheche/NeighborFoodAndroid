package com.epfl.neighborfood.neighborfoodandroid.repositories;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.epfl.neighborfood.neighborfoodandroid.database.Database;
import com.epfl.neighborfood.neighborfoodandroid.database.DatabaseFactory;
import com.epfl.neighborfood.neighborfoodandroid.database.DocumentSnapshot;
import com.epfl.neighborfood.neighborfoodandroid.models.Meal;
import com.epfl.neighborfood.neighborfoodandroid.models.Order;
import com.epfl.neighborfood.neighborfoodandroid.models.OrderStatus;
import com.epfl.neighborfood.neighborfoodandroid.models.User;
import com.epfl.neighborfood.neighborfoodandroid.util.Pair;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealRepository {
    private final static String mealsDataCollectionPath = "Meals";
    private final static String ordersDataCollectionPath = "Orders";

    public MealRepository() {
    }
    public Task<Meal> getMealById(String id){
        if (id == null) {
            return Tasks.forException(new IllegalArgumentException("The meal ID cannot be null"));
        }
        return DatabaseFactory.getDependency().fetch(mealsDataCollectionPath, id).continueWith(task -> {
            if (task.isSuccessful()) {
                return task.getResult().toModel(Meal.class);
            }
            return null;
        });
    }

    /** sends a request to post a meal
     * @param meal the meal to post
     * @return the task containing mealId that may complete, fails if the argument is null, or if the database is unreachable
     */
    public Task<String> postMeal(Meal meal){
        if (meal == null) {
            return Tasks.forException(new IllegalArgumentException("Cannot post a null meal"));
        }
        //We first post the meal to the database,
        return DatabaseFactory.getDependency().add(mealsDataCollectionPath,meal)
                .continueWith(task ->{
                        // and once that is done (and we get the corresponding id of the meal),
                        DatabaseFactory.getDependency().
                                // we need to update the mealId field stored in the database
                                set(mealsDataCollectionPath,task.getResult(),meal.copyWithId(task.getResult()));
                    return task.getResult();
                });

    }

    /** Fetches all the meals stored in the database
     * @return the task that may complete and contains the meals
     */
    public Task<List<Meal>> getAllMeals(){
        return DatabaseFactory.getDependency().fetchAll(mealsDataCollectionPath).continueWith(t->{
            ArrayList<Meal> res = new ArrayList<>();
            if(t.isSuccessful()){
                for (DocumentSnapshot m: t.getResult().getDocuments()) {
                    res.add(m.toModel(Meal.class) );
                }
            }
            return res;
        });
    }
    /** Fetches all the unassigned meals stored in the database
     * @return the task that may complete and contains the unassigned orders and meals corresponding to it.
     */
    public Task<List<Pair<Order, Meal>>> getAllUnassignedMeals(){
        return DatabaseFactory.getDependency().fetchAllMatchingAttributeValue(ordersDataCollectionPath,"orderStatus", OrderStatus.unassigned).continueWith(to->{
            ArrayList<Pair<Order,Meal>> res = new ArrayList<>();
            if(to.isSuccessful()){
                for (DocumentSnapshot o: to.getResult().getDocuments()) {
                    getMealById(o.toModel(Order.class).getMealId()).addOnSuccessListener(meal->{
                            res.add(Pair.of(o.toModel(Order.class),meal));}
                    );
                }
            }
            return res;
        });
    }


}
