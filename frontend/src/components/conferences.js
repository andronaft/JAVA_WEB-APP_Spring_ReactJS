import React from "react";
import { getJson, postForm } from "../api";
import { loadUser } from "../session";
import FormRemoveParticipant from '../form/fromRemoveParticipant';
import FormCancelConf from '../form/formCancelConf';
import FormJoinConf from "../form/formJoinConf";
import FormChangeTimeConf from "../form/formChangeTimeConf";
import Error from "./error";
import LinkBtn from './LinkBtn';


class Conferences extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            user_role: undefined,
            user_id: undefined,
            dataa: [],
            error: undefined
        };
    }

    componentDidMount(){
        const user = loadUser();
        this.setState({user_role: user.user_role, user_id: user.user_id});
        this.loadConferences();
    }

    loadConferences = async () => {
        const data = await getJson('/getAllConference');
        if (Array.isArray(data.arrayList)) {
            this.setState({dataa: data.arrayList});
        } else {
            this.setState({error: data[0]});
        }
    }

    // sends the form, shows the answer and reloads the list
    submit = async (url, params) => {
        const data = await postForm(url, params);
        this.setState({error: data[0]});
        await this.loadConferences();
    }

    removeParticipant = (e) => {
        e.preventDefault();
        const elements = e.target.elements;
        return this.submit('/removeParticipantFromConf', {
            id_participant: elements.id_participant.value,
            conference_id: elements.conference_id.value,
            admin_id: elements.id_admin.value,
            admin_password: elements.password.value
        });
    }

    changeTimeConf = (e) => {
        e.preventDefault();
        const elements = e.target.elements;
        return this.submit('/changeConfTime', {
            conference_id: elements.conference_id.value,
            admin_id: elements.id_admin.value,
            admin_password: elements.password.value,
            datee: elements.datee.value,
            timee: elements.timee.value
        });
    }

    cancelConf = (e) => {
        e.preventDefault();
        const elements = e.target.elements;
        return this.submit('/cancelConf', {
            conference_id: elements.conference_id.value,
            admin_id: elements.id_admin.value,
            admin_password: elements.password.value
        });
    }

    joinConf = (e) => {
        e.preventDefault();
        return this.submit('/joinConference', {
            conference_id: e.target.elements.conference_id.value
        });
    }

  render(){
    const { user_role, user_id } = this.state;

    const list = this.state.dataa.map((item) =>{

        return(
            <div className="card" key={item.id}>
                <div className="card-body">
                    <h5 className="card-title"> conference name: {item.name} id: <span className="badge badge-secondary">{item.id}</span></h5>
                    <h6 className="card-subtitle mb-2 text-muted">name of room: {item.name_room}</h6>
                    <h6 className="card-subtitle mb-2 text-muted">participants: {item.amount_participant}/{item.capacity_room}</h6>
                    <h6 className="card-subtitle mb-2 text-muted">{item.datee} {item.timee}</h6>

                    <div className="card-text">{ user_role &&
                        <div>
                            id participant: {item.id_participant}
                            { (user_role==="admin"||user_role==="manager") &&
                            <div>
                                <FormRemoveParticipant removeParticipant={this.removeParticipant}
                                conference_id= {item.id}
                                id_admin = {user_id}/>

                                <FormChangeTimeConf changeTimeConf={this.changeTimeConf}
                                conference_id= {item.id}
                                id_admin = {user_id}/>

                                {user_role==="admin" &&
                                <FormCancelConf cancelConf ={this.cancelConf}
                                conference_id= {item.id}
                                id_admin = {user_id}/>}
                            </div>}
                        </div>}

                        {!user_role && user_id &&
                            <FormJoinConf joinConf ={this.joinConf}
                            conference_id= {item.id}/>
                            }
                    </div>
                </div>
            </div>
        );
    });

    return <div>
                <Error errorr = {this.state.error}/>
                {user_role &&
                    <div className="badge badge-primary newconf"><LinkBtn to="/creatConference" label={'new conference'} /></div>
                }
                {list}
            </div>;

  }
}

export default Conferences;
