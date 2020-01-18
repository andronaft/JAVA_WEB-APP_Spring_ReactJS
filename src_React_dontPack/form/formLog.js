import React from "react";
import LinkBtn from '../components/LinkBtn';

const FormLog = props => (
<form onSubmit={props.startLog}>
        <div className="form-group">
                <label for="email">Login:</label>
                <input type="text" className="form-control" required name="login" placeholder="Login"/>
        </div>
        <div className="form-group">
                <label for="pwd">Password:</label>
                <input type="password" className="form-control" required name="password" placeholder="password" id="pwd"/>
        </div>
        <button type="submit" className="btn btn-primary">Sign in</button>
        <div className="form-group">
                <label className="form-check-label">
                <div className="forget"><LinkBtn to="/registration" label={'Dont have account?'} /></div>
                </label>
        </div>
</form>
)
            
export default FormLog;