## Exercise: WaterState (12 Points)

The project name of this exercise is **WaterState** Problem Description

The purpose of this assignment is for you to write all of your own comments and code. You will also get some practice using enumeration. 

The detailed description of this problem comes from the Programming Exercise P3.9 that is in the book (page 127, of the _Late Objects_ version).

You are to write a program that will take a temperature and units(Celcius or Farenheit). This temperature and units has the temperature as a double followed by either a 'C' or an 'F' for Celcius and Farenheit, respectively. Notice, that there is no space between the temperature and the units. Your program will then output one of three matter states, SOLID, LIQUID or GAS.

Additionally, you should represent the state using an enumeration that contains the matter states SOLID, LIQUID and GAS. See special topic 3.4 (page 105, of the _Late Objects_ vesion) in the book.

Hints:

- You can read in the entire input using Scanner.next().

- You can use the length() method of the String object to figure out how many characters are in your input.

- You can use the charAt() method of the String object to extract a character from your string.

- You can print enumerations directly. For example, `System.out.println(MatterState.SOLID)` prints out `SOLID`.

This problem should be solved by writing code in `public static void main` to get input from the user and `public static String getWaterState(String temperature)` to return the state of the matter at the given temperator. 

Using the test input, your output should look like:

```
Enter a temperature: -10F 
Water state: SOLID
```

### Getting Started

Like our last exercise, we are going to do this exercise by writing the source code that solves the problem first in **WaterState.java**. Using the techniques shown on the web page titled [http://crowd.cs.sbcc.edu:7990/projects/CS105F2016/repos/allan.knight/browse/HowToStartEveryProject.md) create a source file called **WaterState.java**. This is where your code will go. 

Starting this week we don't have any code to copy for the assignment. You get to do it all! Don't forget to provide proper JavaDoc documentation.

Now go through WaterState.java, **add the proper headers as in past assignments** and then change the [CHANGE THIS TO YOUR INFORMATION] text to the proper items. There are two items to be changed.

Once you've written your code run the code by single clicking on **WaterState.java** in the package explorer and selecting **Run->Run** from the menu or using the keyboard shortcut. Examine the output. Does it do what you want? If not, how can you modify the code to do what you want?

### Running the Unit Tests

Next you'll want to run these unit tests. Start by right-clicking on the `unittest.cs105` package and selecting **Run As -> JUnit Test**. 

To go back to the project view, select the **Package Explorer** tab.

### How to turn in this exercise

The first step of turning in your code is to commit and push your code to BitBucket. Once you've completed this step your code will be on BitBucket in your repository, not the repository for the class. This will allow you to use all your projects later as a portfolio.

To start the process write click your project and select **Team -> Commit...** and follow the usual procedures.

#### Completing the turn-in process

Go to **your** repository or the repository for this assignment on BitBucket (the project you forked from to create your project).

<img src="https://dl.dropboxusercontent.com/u/7698973/cs105/EX01-HelloWorld/create-pull-request.png" width="207" height="207" />

1\. Click on the **Create pull request** icon. 

You should see something similar to this picture:

<img src="https://dl.dropboxusercontent.com/u/7698973/cs105/EX01-HelloWorld/pull-request-screen-first.png" width="600" height="250" />

The next screen then shows the source and destination of the pull request. Your code is the source and is listed at the top. The destination is the original project that you forked from. The only thing to do on this screen is to select the **master** branch for your project. Once you select **Select Branch**, as directed below, you should see the following:

<img src="https://dl.dropboxusercontent.com/u/7698973/cs105/EX01-HelloWorld/pull-request-master.png" width="600" height="250" />

2\. Select **Select Branch**

3\. Select **master** 

4\. Press the **Continue** button.

Once you've followed these steps you should see this at the top:

<img src="https://dl.dropboxusercontent.com/u/7698973/cs105/EX01-HelloWorld/pull-request-description.png" width="514" height="325" />

5\. Under **Title**.

6\. Under **Description**.

7\. Finally, click on **Create**
