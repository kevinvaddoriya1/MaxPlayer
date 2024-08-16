package com.example.videoplayer.videoUtils;

public abstract class IEventListener<T> {
    abstract void onSuccess(T data);
    abstract void onFailure();
}