import React from "react";
import axios from 'axios';
import myInitObject from '../index';
import FormLog from "../form/formLog";
import Error from "./error";



class Authorization extends React.Component {
    
    state = {
        login: undefined,
        global_login: undefined,
        password: undefined,
        role: null,
        error: undefined
    }
 
     startLog =  async (e)=> {
     e.preventDefault();
    
     var data = undefined;

     const login = e.target.elements.login.value;
     const password = e.target.elements.password.value;

     await axios.post(`http://localhost:8080/login?&login=${login}&password=${password}`)
     .then(response =>{data = response.data})
     .catch(function(error) {
         console.log('Request failed', error)
    });

       
       if(data.login){
         this.setState({
             login: data.login,
             password: undefined,
             role: data.role,
             error: undefined
         });
        localStorage.removeItem('user_idd');localStorage.removeItem('user_loginn');localStorage.removeItem('user_rolee');
        myInitObject.user_id = data.id; localStorage.setItem('user_idd',data.id);
        myInitObject.user_login = data.login; localStorage.setItem('user_loginn',data.login);
        myInitObject.user_role = data.role; localStorage.setItem('user_rolee',data.role);
       }
       else{
         this.setState({
             login: undefined,
             password: undefined,
             role: null,
             error: data[0]
         });
         return(this.state.error);
       }
     }
     
  render(){
    return(
    <div>
        <div className="container">
            <div className="jumbotron">
            {this.state.login &&
                <Error loginok = {this.state.login}/>
            }
            <FormLog startLog = {this.startLog}/>
            
            <Error errorr = {this.state.error}/>
            </div>
        </div>  
    </div>
  );
  }
}

export default Authorization;