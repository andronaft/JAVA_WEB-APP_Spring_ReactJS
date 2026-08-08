

# JAVA_Spring_ReactJS_WEB_APP
<!--Esta aplicación web utiliza tecnologías como:
* JAVA (lenguaje para servidor REST)
* Spring-Boot (framework de aplicaciones)
* MAVEN (herramienta de construcción de proyectos)
* H2 (BD)
* React-DOM (para la interfaz de usuario)

-----
#  Descripción

Esta aplicación se utiliza para anunciar la conferencia. 
Existen tres grupos de usuarios para este sistema.
1. ***Usuarios predeterminados*** (usuarios no autorizados)
2. ***Usuarios autorizados***
3. ***Administrador***

###  Tienen acceso a diferentes funcionalidades.

> ***Usuarios predeterminados***
> > * Solo pueden ver la información de la conferencia  ![Func defUser](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/defaulrUser_function.png)

> ***Usuarios autorizados***
> > * Pueden unirse a la conferencia
> > * Pueden ver la información de la cuenta  ![Func auzUser](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/authorizationUser_function.png)

> ***Administrador***
> > * Puede eliminar participantes de la conferencia
> > * Puede cancelar la conferencia
> > * Puede crear una nueva conferencia ![Func admin](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/admin_funcrion.png)

---

# Información de la BASE DE DATOS

### Hay tres tablas.

![DB Diagrams](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/Create_Insert_SQLscript_for_BD/BD_Diagram.png)

>>> El script SQL para crear la tabla e insertar los datos iniciales lo puedes encontrar aquí [SQL CREATE](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/tree/master/src/main/resources/Create_Insert_SQLscript_for_BD)

---

# Funcionalidad en el lado de Java

> ***ParticipantDAO***
>> ![ParticipantDao](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/participantDAO.png)

> ***ConferenceDAO***
>> ![ConferenceDao](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/conferenceDAO.png)

> ***RoomMDAO***
>> ![RoomDao](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/roomDAO.png)


---

* El código React sin empaquetar lo encontrarás [aquí](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/tree/master/src/main/resources/src_React_dontPack)

---

```
String lastly = "you'll see  a cool toxic feature"
boolean do_you_scroll_below = (0 == (7 * (4 ^ (2))%(3)));
if (do_you_scroll_below && users.getPresense){
    sout(lastly)
}
```

![feature](https://github.com/andronaft/JAVA_Spring_React_WEB_APP/blob/master/src/main/resources/img_for_README/feature.gif)
