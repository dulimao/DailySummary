// IClientInterface.aidl
package com.example.myandroiddemo;

// Declare any non-default types here with import statements
import com.example.myandroiddemo.service.IPerson;

interface IClientInterface {


  void updateProgress(int progress,out Person person);
}