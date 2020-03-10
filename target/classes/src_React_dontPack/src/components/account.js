import React from "react";
import myInitObject from "../index";
import axios from 'axios';
import Error from './error';

class Account extends React.Component {

    
    state = {
        id: undefined,
        data: undefined,
        notLogin: undefined
    }

    componentDidMount(){
        try{
            myInitObject.user_id=localStorage.getItem('user_idd');
            axios.get(`http://localhost:8080/getAccount?&id=${myInitObject.user_id}`)
            .then(res => {
            console.log(res);
            const data= res.data;
            this.setState({id: myInitObject.user_id ,data: data})
            })  
            
        }catch{console.log("Sorry fo this solution.")}
    }
  
  render(){

    const mes = "You must log in for view information";
    return(
        <div className="container jumbotron">
            {!this.state.id &&
                <div>
                <Error errorr = {mes}/>
                </div>
            }
            {this.state.id && 
                <div className="acc">
                   <div><span className="badge badge-secondary">Login:</span> {this.state.data.login} Id({this.state.data.id}) {this.state.data.role}</div>
                   <div><span className="badge badge-secondary">First Name:</span> {this.state.data.firstName}</div>
                   <div><span className="badge badge-secondary">Last Name:</span> {this.state.data.lastName}</div>
                   <div><span className="badge badge-secondary">Birthday:</span> {this.state.data.birthDay}</div>
                   <div><span className="badge badge-secondary">Join conference:</span> {this.state.data.id_conference_participant}</div>
                </div>
            }
        </div>
    );
  }
}

export default Account;