package com.example.d_tree.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.d_tree.util.Constants;

import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserViewModel extends ViewModel {

    public MutableLiveData<List<User>> users = new MutableLiveData<>();
    public MutableLiveData<FetchUsersState> checkLoadingState = new MutableLiveData<>();

    public void getUsers() {
        checkLoadingState.postValue(FetchUsersState.LOADING);
        UserServiceClient.getInstance().getApi().getUsers(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userList -> {
                            checkLoadingState.postValue(FetchUsersState.SUCCESS);
                            users.postValue(userList);
                        }, error -> {
                            checkLoadingState.postValue(FetchUsersState.ERROR);
                        }

                );
    }
}
