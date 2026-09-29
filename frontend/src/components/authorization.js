import React from "react";
import { postForm } from "../api";
import { saveUser } from "../session";
import FormLog from "../form/formLog";
import Error from "./error";


class Authorization extends React.Component {

    state = {
        login: undefined,
        role: null,
        error: undefined
    }

    startLog = async (e) => {
        e.preventDefault();

        const login = e.target.elements.login.value;
        const password = e.target.elements.password.value;

        const data = await postForm('/login', {login, password});

        if (data.login) {
            saveUser(data);
            this.setState({
                login: data.login,
                role: data.role,
                error: undefined
            });
        } else {
            this.setState({
                login: undefined,
                role: null,
                error: data[0]
            });
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
