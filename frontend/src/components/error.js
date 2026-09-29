import React from "react";

const Error = props => (
    <div>
        {props.errorr&&
        <div className="alert alert-primary" role="alert">
        <p>{props.errorr}</p>
        </div>
        }
        {props.loginok && 
            <div className="alert alert-primary" role="alert">
            <p>You login as {props.loginok}</p>
        </div>
        }
    </div>
  );

export default Error;