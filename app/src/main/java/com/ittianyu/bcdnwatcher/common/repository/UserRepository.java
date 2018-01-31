package com.ittianyu.bcdnwatcher.common.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.ittianyu.bcdnwatcher.common.bean.IncomeBean;
import com.ittianyu.bcdnwatcher.common.bean.IndexBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.MinerBean;
import com.ittianyu.bcdnwatcher.common.bean.UserBean;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.livedata.LiveDataObservableAdapter;
import com.ittianyu.bcdnwatcher.common.repository.remote.RemoteUserDataSource;

import java.util.List;


/**
 * Created by 86839 on 2017/10/6.
 */

public class UserRepository {
    private static final UserRepository instance = new UserRepository();

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        return instance;
    }


    private Context context;

    private UserDataSource userRds = RemoteUserDataSource.getInstance();


    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    public LiveData<Lcee<UserBean>> login(String phone, String password, String areaCode) {
        return LiveDataObservableAdapter.fromObservableLcee(userRds.login(phone, password, areaCode));
    }

    public LiveData<Lcee<IndexBean>> queryTotalIncome(String phone, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(userRds.queryTotalIncome(phone, token));
    }

    public LiveData<Lcee<IncomeBean>> queryIncomeHistory(String phone, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(userRds.queryIncomeHistory(phone, token));
    }

    public LiveData<Lcee<MinerBean>> queryMiners(String phone, String areaCode, String token) {
        return LiveDataObservableAdapter.fromObservableLcee(userRds.queryMiners(phone, areaCode, token));
    }

    public LiveData<Lcee<List<WatcherItemBean>>> queryWatcherList() {
        return LiveDataObservableAdapter.fromObservableLcee(userRds.queryWatcherList());
    }

}