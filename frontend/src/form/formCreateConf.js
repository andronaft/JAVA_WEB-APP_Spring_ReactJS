import React from "react";

const FormCreateConf = props => (
   <div className="jumbotron">
    <form onSubmit={props.createNewConference}>
        <div className="form-group">
            <label htmlFor="conf-name">Conference name</label>
            <input type="text" className="form-control" required name="name" id="conf-name" placeholder="conference name" maxLength="150"/>
        </div>
        <div className="form-group">
            <label htmlFor="conf-room">Room id</label>
            <input type="number" min="1" className="form-control" required name="id_room" id="conf-room" placeholder="id room"/>
        </div>
        <div className="form-group">
            <label htmlFor="conf-date">Conference date</label>
            <input type="date" className="form-control" required name="datee" id="conf-date" placeholder="date"/>
        </div>
        <div className="form-group">
            <label htmlFor="conf-time">Conference time</label>
            <input type="time" className="form-control" required name="timee" id="conf-time" placeholder="time"/>
        </div>
        <div className="form-group">
            <label htmlFor="conf-password">Your password</label>
            <input type="password" className="form-control" required name="admin_password" id="conf-password" placeholder="your password"/>
        </div>
        <button className="btn btn-primary">Create new Conference</button>
    </form>
   </div>
)

export default FormCreateConf;
