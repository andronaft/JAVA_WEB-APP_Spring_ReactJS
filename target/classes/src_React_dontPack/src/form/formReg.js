import React from "react";
import LinkBtn from '../components/LinkBtn';

const FormReg = props => (
<div className="jumbotron">
        <form onSubmit={props.startReg}>
                <div className="form-group">
                        <input type="text" className="form-control" required name="login" placeholder="Login"></input>
                </div>
                <div className="form-group">
                        <input type="password" className="form-control" required name="password" placeholder="password"></input>
                </div>
                <div className="form-group">
                        <input type="text" className="form-control" required name="firstName" placeholder="First Name"></input>
                </div>
                <div className="form-group">
                        <input type="text" className="form-control"  required name="lastName" placeholder="Last Name"></input>
                </div>
                <div className="form-group">
                        <input type="date" className="form-control" required name="birthday" placeholder="Date of burning"></input>
                </div>  
                <button  className="btn btn-primary">Sing up</button>
                <div className="forget"><LinkBtn to="/authorization" label={'Already have account?'} /></div>
        </form>
</div>
)
            
export default FormReg;