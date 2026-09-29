import React from "react";
import { postForm } from "../api";
import { saveUser } from "../session";
import FormReg from "../form/formReg";
import Error from "./error";


class Registration extends React.Component {
   state = {
       firstName: undefined,
       error: undefined
   }

    startReg = async (e) => {
        e.preventDefault();
        const elements = e.target.elements;

        const data = await postForm('/register', {
            firstname: elements.firstName.value,
            lastname: elements.lastName.value,
            birthday: elements.birthday.value,
            login: elements.login.value,
            password: elements.password.value
        });

        if (data.login) {
            // the server logs the new user in right away
            saveUser(data);
            this.setState({firstName: data.firstName, error: undefined});
        } else {
            this.setState({firstName: undefined, error: data[0]});
        }
    }

  render(){

    return(
        <div>
            { this.state.firstName &&
                <Error loginok= {this.state.firstName}/>
            }
            <FormReg startReg = {this.startReg}/>
            <Error errorr = {this.state.error}/>
        </div>
    );
  }
}

export default Registration;
