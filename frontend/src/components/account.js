import React from "react";
import { getJson, postForm } from "../api";
import { clearUser, loadUser } from "../session";
import Error from './error';

class Account extends React.Component {

    state = {
        id: undefined,
        data: undefined,
        error: undefined
    }

    componentDidMount(){
        if (!loadUser().user_id) {
            return;
        }
        getJson('/getAccount').then(data => {
            if (data.login) {
                this.setState({id: data.id, data: data});
            } else {
                // the server session has expired
                clearUser();
                this.setState({error: data[0]});
            }
        });
    }

    logout = async () => {
        await postForm('/logout');
        clearUser();
        this.setState({id: undefined, data: undefined, error: undefined});
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
                   <button className="btn btn-secondary mt-3" onClick={this.logout}>Log out</button>
                </div>
            }
        </div>
    );
  }
}

export default Account;
