import React from "react";

const FormJoinConf = props => (
    <form onSubmit={props.joinConf}>
        <input type="hidden" name="conference_id" value={props.conference_id}/>
        <button className="btn btn-primary">Join conference</button>
    </form>
)

export default FormJoinConf;
