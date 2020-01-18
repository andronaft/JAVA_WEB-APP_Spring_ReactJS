import React from "react";
import axios from 'axios';
import FormCreateConf from '../form/formCreateConf';
import myInitObject from '../index';
import Error from "./error";


class CreatConference extends React.Component {
    state = {
        data: undefined,
        message: undefined,
        admin_id: myInitObject.user_id
    }

    componentDidCatch(){
        this.setState({admin_id: myInitObject.user_id});
    }
    creatConference =  async (e)=> {
        e.preventDefault();
        var data = undefined;
        const name = e.target.elements.name.value;
        const id_room = e.target.elements.id_room.value;
        const datee = e.target.elements.datee.value;
        const timee = e.target.elements.timee.value;
        const admin_password = e.target.elements.admin_password.value;

        console.log(name,id_room,datee,timee,admin_password,this.state.admin_id);
   
        await axios.post(`http://localhost:8080/createconf?&name=${name}&id_room=${id_room}&datee=${datee}&timee=${timee}:00&admin_id=${this.state.admin_id}&admin_password=${admin_password}`)
        .then(response =>{data = response.data})
        .catch(function(error) {
            console.log('Request failed', error)
          });

        this.setState({
            message: data[0]
        });
    }

    render(){
        return(
            <div>
                <FormCreateConf createNewConference={this.creatConference}
                admin_id={this.state.admin_id}/>
                <Error errorr = {this.state.message}/>
            </div>);
    }
}
export default CreatConference;