# JAVA_Spring_React_WEB_APP
<!--This Web-app uses technologies such as:
* JAVA (language for Rest sserver)
* Spring-Boot(application framework)
* MAVEN (project builder)
* H2 (DB)
* React-DOM (for UI interface)

-----
#  Agenda

This app is used to announce the conference. 
There are three user clusters for this .
1. ***Default users*** (unauthorized users)
2. ***Authorized users***
3. ***Admin***

###  They have access to different functionalities.

> ***Default users***
> > * Сan only watch conference information  ![Func defUser](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/defaulrUser_function.png)

> ***Athorized users***
> > * Can join the conference
> > * Can view account information  ![Func auzUser](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/authorizationUser_function.png)

> ***Admin***
> > * Can delete participants from conference
> > * Can cancel conference
> > * Can create new conference ![Func admin](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/admin_funcrion.png)

---

# DATA BASE information

### There are three tables.

![DB Diagrams](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/Create_Insert_SQLscript_for_BD/BD_Diagram.png)

>>> SQL script for creating table and insert start data you can find here [SQL CREATE](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/tree/master/src/main/resources/Create_Insert_SQLscript_for_BD)

---

# Functionality on the Java side

> ***ParticipantDAO***
>> ![ParticipantDao](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/participantDAO.png)

> ***ConferenceDAO***
>> ![ConferenceDao](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/conferenceDAO.png)

> ***RoomMDAO***
>> ![RoomDao](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/roomDAO.png)


---

* Not packaged react you will find [here](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/tree/master/src/main/resources/src_React_dontPack)

---

```
String lastly = "you'll see  a cool toxic feature"
boolean do_you_scroll_below = (0 == (7 * (4 ^ (2))%(3)));
if (do_you_scroll_below && users.getPresense){
    sout(lastly)
}
```

![feature](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/feature.gif)





