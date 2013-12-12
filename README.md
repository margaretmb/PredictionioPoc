Prediction.io For Fun and Profit
================================


1. Downloading and Installing
-----------------------------

In the "real world" we will probably be running this on an AWS instance, but for the purposes of this POC, I installed an instance locally. The provided instructions here (http://docs.prediction.io/current/installation/install-predictionio-on-linux.html) are very good for getting prediction.io set up on a Linux box. If you get some connection refused errors, simply running the installation again may take care of them. Make sure that you are able to ssh to localhost, though -- the instructions aren't kidding about that one.


2. Somebody Set Us Up the App
-----------------------------

Once you're up and running, go to localhost:9000. If you set up a user account in the tutorial steps above, go ahead and log in with it. Otherwise, set one up now by running bin/users in a shell in your prediction.io directory. Once logged in, you should see an option to add an app. You can name the app whatever you like; since this is just a demo, the app name doesn't really matter. I called mine BigDamnedApp because I like Firefly references, but I digress.

Once you see the app in your list of applications, click "develop." You should now see a long string of text labeled app key. This is what you will need to run the app. 

At this point, we turn to the actual code that is in this handy dandy git repository. In the src/test/java folder you will find a suite of Unit Tests called BigDamnedTestSuite (creative, I know). These tests don't actually test much, but they seemed a good way to showcase the power of prediction.io without having to create a full blown webapp. 

In the before() method, change the app key to your app key. 

Please do not run all the unit tests at once. Well, you can if you really want, but all but the first one will result in failing conditions. Instead, I will go through the tests one at a time and explain what they mean.


3. Importing All The Things
---------------------------

Go ahead and run the first unit test, clientShouldImportAllTheThings(). This one takes quite awhile to run, so you might want to go grab some coffee. I can wait.

...

Okay, back? Chances are the test is still running, so now I'll explain what it's actually doing. This is, by the way, a modified version of the Python-based movie-recommendation tutorial found here (http://docs.prediction.io/current/tutorials/movie-recommendation.html), so you can check that out if you want. This method basically parses a database full of movies, users, and the users' ratings of various movies, and adds them to our prediction.io app. It's quite a bit of data, hence the long load time. But once it's done, we'll be able to use prediction.io to recommend the users some movies.


4. Training Your First Engine
-----------------------------

Once the data is finally done importing (or you can do this while it's importing), it's time to go back to the web admin interface and create an engine. On the develop page for your app, click on "create an engine." For the first demo, you'll want an item recommendation engine. I called mine BigDamnEngine. You can name it something else, but if you do, you'll need to change the engineName variable in the clientShouldRecommendAllTheThings() method. Feel free to tweak any of the settings in the engine; but most importantly please do change Unseen Items Only to "Recommend Unseen Items Only," otherwise the user will be recommended things they've already seen.

The top of the page should display an orange box that says something like this: 

Engine Status: Not Running: Training the first data model.

If it for whatever reason doesn't train right away, or you want to train again (say you changed a setting and want to re-train the data), you can go to the algorithms tab, select the menu where it says "running," and click "Train Data Model Now." Yes, I thought this was an innane and hard-to-find place to put that option as well, but there you have it. Here in the algorithms tab you can also change the algorithm used to generate recommendations. I've found the default one works best, as these prediction.io folks know what they're doing, but it never hurts to play around.

If at any point the status box goes from "Training the first data model" to "Waiting for the the first-time data model training," that means that Something Went Wrong. To debug, it's best to check the logs. I've found that logs/scheduler.err and the Hadoop logs (found in the vendors directory) are a good place to start. 

If this is the first time you've run prediction.io, it should (famous last words) go without a hitch. However, prediction.io, and more specifically Hadoop, seems to exhibit some finicky quirks if the server was shut down improperly (ie your laptop ran out of batteries), at which point Hadoop absolutely refuses to connect to the NameNode server at port 9001 (insert over 9000 joke here). At this point, I've found the quickest and dirtiest approach is to simply re-install prediction.io, but as I'm sure you'd rather not run that import code again if you don't have to, if you know of a more elegant solution I'd be happy to hear it.

Assuming, however, that the training goes off without a hitch, the status box should change into a blue box that says "Running." 


5. Movie Recommendations for a User
-----------------------------------

Now, at long last, you can run the second unit test, clientShouldRecommendAllTheThings(). For this test, I created a fake user with a love of raunchy action-comedies. The default algorithm tends to recommend the user a lot of action movies, but tweaking the algorithm will also tweak the results. Many of the algorithms generate recommendations based on what other users with similar preferences also enjoyed, so I suppose people who like raunchy action comedies tend to like action movies, also (at least according to the imported data).

One thing I have found about prediction.io is that unfortunately, the option to optimize the results based on whether they would watch or rate highly doesn't seem to do much -- it seems to recommend the same things no matter what. However, some tweaking of the algorithm that I haven't discovered yet may be able to help this. 



6. Similar Item Recommendations
-------------------------------

prediction.io also has functionality to recommend movies that are similar to other movies. For demonstration purposes, I chose Apollo 13, because it's a pretty swell movie. Create another engine in the web interface, only this time make it an item similarity engine. For mine, I called it BigDamnSimilarEngine, but again, you can call it whatever you like as long as you change it in the unit test. It will need to train data again for the new engine, so just follow the steps from part 4 and also possibly get another coffee (you'll be quite caffeinated by the end of this). For this one, I found the default algorithm worked very well, and recommended a lot of things I'd think of when thinking of Apollo 13 -- other Tom Hanks movies, other space-themed movies, and so on. 
