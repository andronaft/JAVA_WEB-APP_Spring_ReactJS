import React from "react";
import axios from 'axios';
import myInitObject from '../index';
import FormRemoveParticipant from '../form/fromRemoveParticipant';
import FormCancelConf from '../form/formCancelConf';
import Error from "./error";
import LinkBtn from './LinkBtn';
import FormJoinConf from "../form/formJoinConf";


class Conferences extends React.Component {
    constructor(props){
        
        super(props);
        this.state={
            user_role: undefined,
            dataa: [],
            error: undefined
            
        };
        console.log("Constructor. Just to undestud circle react");
    }

    componentDidMount(){
        try{
            myInitObject.user_id = localStorage.getItem("user_idd");
            myInitObject.user_login = localStorage.getItem("user_loginn");
            if(localStorage.getItem("user_rolee")=='admin'){
                myInitObject.user_role = localStorage.getItem("user_rolee");
            }
        }
        catch{
            //sorry
        }
        console.log("DidMount. Just to undestud circle react");
        axios.get(`http://localhost:8080/getAllConference?`)
      .then(res => {
          console.log(res);
        const persons = res.data.arrayList;
        this.setState({dataa: persons , user_role: myInitObject.user_role});
      })  
    }

    removeParticipant =  async (e)=> {
        e.preventDefault();
        var data = undefined;
        const id_participant = e.target.elements.id_participant.value;
        const password = e.target.elements.password.value;
        const conference_id = e.target.elements.conference_id.value;
        const admin_id = e.target.elements.id_admin.value;
        console.log(id_participant, password, conference_id, admin_id);
        await axios.post(`http://localhost:8080/removeParticipantFromConf?&id_participant=${id_participant}&conference_id=${conference_id}&admin_id=${admin_id}&admin_password=${password}`)
        .then(response =>{data = response.data})
        .catch(function(error) {
            console.log('Request failed', error)
          });
          this.setState({
              error: data[0]
          });
    }

    cancelConf =  async (e)=> {
        e.preventDefault();
        var data = undefined;
        const password = e.target.elements.password.value;
        const conference_id = e.target.elements.conference_id.value;
        const admin_id = e.target.elements.id_admin.value;
        console.log( password, conference_id, admin_id);
        await axios.post(`http://localhost:8080/cancelConf?&conference_id=${conference_id}&admin_id=${admin_id}&admin_password=${password}`)
        .then(response =>{data = response.data})
        .catch(function(error) {
            console.log('Request failed', error)
          });
          this.setState({
              error: data[0]
          });
    }

    joinConf =  async (e)=> {
        e.preventDefault();
        var data = undefined;
        const conference_id = e.target.elements.conference_id.value;
        const admin_id = e.target.elements.id_admin.value;
        console.log(  conference_id, admin_id);
        await axios.post(`http://localhost:8080/joinConference?&conference_id=${conference_id}&user_id=${admin_id}`)
        .then(response =>{data = response.data})
        .catch(function(error) {
            console.log('Request failed', error)
          });
          this.setState({
              error: data[0]
          });
    }  
    
  render(){
    
    const list = this.state.dataa.map((item, index) =>{
        
        return( 
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title"> conference name: {item.name} id: <span class="badge badge-secondary">{item.id}</span></h5>
                    <h6 className="card-subtitle mb-2 text-muted">name of room: {item.name_room}</h6>
                    <h6 className="card-subtitle mb-2 text-muted">capacity of room {item.capacity_room}/{item.amount_participant}</h6>
                    <h6 className="card-subtitle mb-2 text-muted">{item.datee} {item.timee}</h6>

                    <div className="card-text">{ myInitObject.user_role && 
                        <div>
                            id parcticipant: {item.id_participant}
                            <FormRemoveParticipant removeParticipant={this.removeParticipant}
                            conference_id= {item.id}
                            id_admin = {myInitObject.user_id}/>
                            <FormCancelConf cancelConf ={this.cancelConf}
                            conference_id= {item.id}
                            id_admin = {myInitObject.user_id}/>
                        </div>}

                        {!myInitObject.user_role && myInitObject.user_id &&
                            <FormJoinConf joinConf ={this.joinConf}
                            conference_id= {item.id}
                            id_admin = {myInitObject.user_id}/>
                            }
                    </div>
                </div>
            </div>            
        );
    });

    console.log("Render. Just to undestud circle react")
    return <div>
                <Error errorr = {this.state.error}/>
                {this.state.user_role && 
                    <div className="badge badge-primary newconf"><LinkBtn to="/creatConference" label={'new conference'} /></div>
                }
                {list}
            </div>;

  }
}

export default Conferences;