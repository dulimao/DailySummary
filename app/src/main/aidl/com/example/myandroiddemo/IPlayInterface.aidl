// IPlayInterface.aidl
package com.example.myandroiddemo;

// Declare any non-default types here with import statements
import com.example.myandroiddemo.IClientInterface;
import com.example.myandroiddemo.service.IPerson;
interface IPlayInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void resetProgress(int progress);
    void setPlayCallback(IClientInterface clientCnterface,inout Person person);
    void setPersons(inout List<Person> persons);
}