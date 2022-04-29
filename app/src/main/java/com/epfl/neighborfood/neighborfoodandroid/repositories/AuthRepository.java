package com.epfl.neighborfood.neighborfoodandroid.repositories;

import androidx.lifecycle.MutableLiveData;

import com.epfl.neighborfood.neighborfoodandroid.authentication.Authenticator;
import com.epfl.neighborfood.neighborfoodandroid.authentication.AuthenticatorFactory;
import com.epfl.neighborfood.neighborfoodandroid.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * The entry point to the authentication repository
 */
public class AuthRepository {

    protected final Authenticator authenticator;
    protected MutableLiveData<User> userLiveData;
    protected MutableLiveData<Boolean> loggedInLiveData;

    public AuthRepository() {
        this.userLiveData = new MutableLiveData<>();
        this.loggedInLiveData = new MutableLiveData<>();
        this.authenticator = AuthenticatorFactory.getDependency();
        if (authenticator.getCurrentUser() != null) {
            userLiveData.postValue(authenticator.getCurrentUser());
            loggedInLiveData.postValue(true);
        }
    }

    /**
     * Request updates for the currently logged in user from the authenticator
     *
     * @param user
     */
    public void updateUser(User user) {
        userLiveData.postValue(user);
        loggedInLiveData.postValue(user != null);

    }

    /**
     * requests the authenticator to log out the current usser
     */
    public void logOut() {
        authenticator.logOut();
        loggedInLiveData.postValue(false);
        userLiveData.postValue(null);
    }

    /**
     * @return
     */
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    /**
     * get an observable objec on the current authentication state of the user
     *
     * @return the Logged In boolean live data
     */
    public MutableLiveData<Boolean> getLoggedInLiveData() {
        return loggedInLiveData;
    }

    /**
     * Requests the authenticator to log in with a google account
     *
     * @param googleSignInAccount : the google account that was signed in
     */
    public void logInWithGoogleAccount(GoogleSignInAccount googleSignInAccount) {
        authenticator.logInWithGoogleAccount(googleSignInAccount).addOnCompleteListener(task -> {
            userLiveData.postValue(task.isSuccessful() ? authenticator.getCurrentUser() : null);
            loggedInLiveData.postValue(task.isSuccessful());
        });
    }


}
