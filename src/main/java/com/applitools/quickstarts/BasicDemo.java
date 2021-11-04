package com.applitools.quickstarts;

import com.applitools.eyes.appium.Eyes;
import com.applitools.eyes.appium.Target;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.visualgrid.model.AndroidDeviceInfo;
import com.applitools.eyes.visualgrid.model.AndroidDeviceName;
import com.applitools.eyes.visualgrid.model.DeviceAndroidVersion;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BasicDemo {

    public static void main(String[] args) throws MalformedURLException {
        VisualGridRunner visualGridRunner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
        Eyes eyes = new Eyes(visualGridRunner);
        eyes.setApiKey("API_KEY_HERE");

        // Configure grid devices to render on
        Configuration configuration = eyes.getConfiguration();
        configuration.addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Pixel_4));
        configuration.addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Pixel_4_XL, ScreenOrientation.LANDSCAPE));
        configuration.addMobileDevice(new AndroidDeviceInfo(AndroidDeviceName.Pixel_3_XL, ScreenOrientation.LANDSCAPE, DeviceAndroidVersion.LATEST));

        eyes.setConfiguration(configuration);

        // Create an AndroidDriver instance to automate your app
        AndroidDriver driver = startApp();
        try {
            // Visually validate the stickers dialog
            eyes.open(driver, "My Android App", "Java Appium Android UFG Demo");
            eyes.check("Check", Target.window());
            eyes.closeAsync();
        } finally {
            driver.quit();
            eyes.abortAsync();
            visualGridRunner.getAllTestResults();
        }
    }

    private static AndroidDriver startApp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("deviceName", "Pixel 4");
        capabilities.setCapability("deviceName", "Pixel 2");
        String appPath = new File("").getAbsolutePath() + "/src/main/resources/app-debug.apk";
        capabilities.setCapability("app", appPath);
//        capabilities.setCapability("platformVersion", "11");
        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("newCommandTimeout", 300);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("noReset", true);
        return new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
    }
}
