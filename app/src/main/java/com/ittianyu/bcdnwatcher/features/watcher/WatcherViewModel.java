package com.ittianyu.bcdnwatcher.features.watcher;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.bean.Lcee;
import com.ittianyu.bcdnwatcher.common.bean.WatcherItemBean;
import com.ittianyu.bcdnwatcher.common.repository.AccountRepository;
import com.ittianyu.bcdnwatcher.common.repository.UserRepository;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by yu on 2017/10/4.
 */

public class WatcherViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private AccountRepository accountRepository = AccountRepository.getInstance();
    // query all
    private MutableLiveData<Long> ldRefreshTime;
    private LiveData<Lcee<List<WatcherItemBean>>> ldItems;

    public LiveData<Lcee<List<WatcherItemBean>>> getItems() {
        if (null == ldItems) {
            ldRefreshTime = new MutableLiveData<>();
            ldItems = Transformations.switchMap(ldRefreshTime, new Function<Long, LiveData<Lcee<List<WatcherItemBean>>>>() {
                private long lastTime;
                @Override
                public LiveData<Lcee<List<WatcherItemBean>>> apply(Long time) {
                    // 防止重复刷新
                    Logger.d("lastTime:" + lastTime + ",time:" + time);
                    if (lastTime == time)
                        return null;
                    lastTime = time;
                    return userRepository.queryWatcherList();
                }
            });
        }
        return ldItems;
    }

    public void reload() {
        ldRefreshTime.setValue(System.currentTimeMillis());
    }

    public LiveData<Lcee<Object>> deleteAccount(AccountBean account) {
        return accountRepository.delete(account);
    }

}
