# Introduction
This is the joke app for the Udacity Android course.


# Design Choices
## Progress Bar
The instructions suggested adding an interstitial and a progress indicator. In reality, that's a
little silly. Retrieving the joke is very quickly (on a local environment at least). A good app
would show the interstitial and load the joke in parallel. By the time the user has a chance to 
dismiss the interstitial, the joke is already loaded.

However to play along with the instructions, both steps are implemented one after the next. Still
seeing the progress bar is very hard because it happens so fast. To make it visible, there is
code in JokeRetrieverAsyncTask that can be uncommented to sleep for 10 seconds.


## Source Of Jokes
The jokes were retrieved from a public service that provides jokes. The class JokeGenerator
connects to that API and retrieves 100 jokes. The jokes are saved into a json file. The json file
is put into the resources of the joke library on the cloud side manually. The JokeGenerator is
currently not invoked by anything.

All the jokes are served from the json file that has been previously generated.


# Coding Standard
- I am a conscientious dissenter of prefixing field names with the letter 'm'. This app follows
the Google coding standard, not that of the Android team. There are plenty of intelligent arguments
on the Internet by leaders of the community why the prefixes are bad. If you are looking for a
pointer to get started on this topic, here you go: 
http://jakewharton.com/just-say-no-to-hungarian-notation/

- The Udacity coding standard asks for all public methods to be commented. I did NOT comment
obvious methods. For example, a 'newInstance' method on a fragment is probably going to create a
new instance of the fragment.


# Notes
- You will find some unused methods in the utility classes. I've created a few utility classes that
I use on my projects. I copy them from one project to the next. So, I didn't create them from
scratch for this one.

- I've used stackoverflow and the Android documentation quite a bit. There are individual lines of
code or short snippets copy-pasted and modified in the code all over the place. For example, I've
lifted the code to check for WiFi and adjusted it for my needs.
