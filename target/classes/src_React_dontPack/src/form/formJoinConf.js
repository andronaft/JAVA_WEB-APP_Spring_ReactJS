import React from "react";

const FormJoinConf = props => (
    <form onSubmit={props.joinConf}>
        <input type="hidden" name="conference_id" value={props.conference_id}/>
        <input type="hidden" name="id_admin" value={props.id_admin}/>
        <button className="btn btn-primary">Join conference</button>
    </form>
)
            
export default FormJoinConf;