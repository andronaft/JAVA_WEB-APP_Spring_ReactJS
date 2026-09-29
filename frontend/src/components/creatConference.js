import React from "react";
import { postForm } from "../api";
import { loadUser } from "../session";
import FormCreateConf from '../form/formCreateConf';
import Error from "./error";


class CreatConference extends React.Component {
    state = {
        message: undefined,
        admin_id: undefined
    }

    componentDidMount(){
        this.setState({admin_id: loadUser().user_id});
    }

    creatConference = async (e) => {
        e.preventDefault();
        const elements = e.target.elements;

        const data = await postForm('/createconf', {
            name: elements.name.value,
            id_room: elements.id_room.value,
            datee: elements.datee.value,
            timee: elements.timee.value,
            admin_id: this.state.admin_id,
            admin_password: elements.admin_password.value
        });

        this.setState({message: data[0]});
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
