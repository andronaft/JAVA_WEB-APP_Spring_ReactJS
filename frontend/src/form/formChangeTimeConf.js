import React from "react";

const FormChangeTimeConf = props => (
    <form onSubmit={props.changeTimeConf}>
            <input type="date" className="form-control" required name="datee" placeholder="date"/>
            <input type="time" className="form-control" required name="timee" placeholder="time"/>
            <input type="hidden" name="conference_id" value={props.conference_id}/>
            <input type="hidden" name="id_admin" value={props.id_admin}/>
            <input type="password" className="form-control" required name="password" placeholder="password"/>
        <button className="btn btn-primary">Change time</button>
    </form>
)

export default FormChangeTimeConf;
