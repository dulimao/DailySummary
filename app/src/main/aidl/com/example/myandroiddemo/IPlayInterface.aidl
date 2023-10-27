// IPlayInterface.aidl
package com.example.myandroiddemo;

// Declare any non-default types here with import statements
import com.example.myandroiddemo.IClientInterface;
interface IPlayInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void resetProgress(int progress);
    void setPlayCallback(IClientInterface clientCnterface);
}