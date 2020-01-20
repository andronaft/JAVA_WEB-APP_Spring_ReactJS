import React from "react";
import axios from 'axios';
import myInitObject from '../index';
import FormReg from "../form/formReg";
import Error from "./error";


class Registration extends React.Component {
   state = {
       firstName: undefined,
       lastName: undefined,
       birthday: undefined,
       login: undefined,
       password: undefined,
       role: null,
       error: undefined
   }
   
    startReg =  async (e)=> {
    e.preventDefault();
    var data = undefined;
    const firstName = e.target.elements.firstName.value;
    const lastName = e.target.elements.lastName.value;
    const birthday = e.target.elements.birthday.value;
    const login = e.target.elements.login.value;
    const password = e.target.elements.password.value;
    
    await axios.post(`http://localhost:8080/register?firstname=${firstName}&lastname=${lastName}&birthday=${birthday}&login=${login}&password=${password}`)
    .then(response =>{data = response.data})
    .catch(function(error) {
        console.log('Request failed', error)
      });
     
      if(data.firstName){
        this.setState({
            firstName: data.firstName,
            lastname: data.lastname,
            birhtday: data.birhtday,
            login: data.login,
            password: undefined,
            role: data.role,
            error: undefined
        });

        localStorage.removeItem('user_idd');localStorage.removeItem('user_loginn');localStorage.removeItem('user_rolee');
        myInitObject.user_id = data.id; localStorage.setItem('user_idd',data.id);
        myInitObject.user_login = data.login; localStorage.setItem('user_loginn',data.login);
        myInitObject.user_role = data.role; localStorage.setItem('user_rolee',data.id.role);
        
      }
      else{
        this.setState({
            firstName: undefined,
            lastname: undefined,
            birthday: undefined,
            login: undefined,
            password: undefined,
            role: null,
            error: data[0]
        });
        console.log(this.state.error + "It`s my first app on React, sorry. I try:D");
      }
    }
  
  render(){

    return(
        <div>
            { this.state.firstName &&
                <div>
                <p><Error loginok= {this.state.firstName}/></p>
                </div>}
            <FormReg startReg = {this.startReg}/>
            <Error errorr = {this.state.error}/> 
        </div>
    );
  }
}

export default Registration;