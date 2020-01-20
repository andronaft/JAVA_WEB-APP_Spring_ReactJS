import React from "react";

const FormRemoveParticipant = props => (
    <form onSubmit={props.removeParticipant}>
        <div className="form-group">
            <input type="hidden" name="conference_id" value={props.conference_id}/>
            <input type="text" className="form-control" name="id_participant" placeholder="id parcticipant"/>
        </div>
        <div className="form-group">
            <input type="hidden" name="id_admin" value={props.id_admin}/>
            <input type="password" className="form-control" name="password" placeholder="password"/>
        </div>
        <button className="btn btn-primary">REMOVE partic</button>
    </form>
)
            
export default FormRemoveParticipant;