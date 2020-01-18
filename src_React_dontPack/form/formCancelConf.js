import React from "react";

const FormCancelConf = props => (
    <form onSubmit={props.cancelConf}>
        <div className="form-group mt-5">
            <input type="hidden" name="conference_id" value={props.conference_id}/>
            <input type="hidden" name="id_admin" value={props.id_admin}/>
            <input type="password" className="form-control" name="password" placeholder="password"/>
        </div>
        <button className="btn btn-primary">CANCEL conf</button>
    </form>
)
            
export default FormCancelConf;