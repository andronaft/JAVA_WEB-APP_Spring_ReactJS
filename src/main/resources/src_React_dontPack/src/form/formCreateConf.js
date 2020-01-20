import React from "react";

const FormRemoveParticipant = props => (
   <div className="jumbotron">
    <form onSubmit={props.createNewConference}>
        <div className="form-group">
            <label for="exampleInputEmail1">Conference name</label>
            <input type="text" className="form-control" required name="name" placeholder="conference name" maxlength="150"/>
        </div>
        <div className="form-group">
            <label for="exampleInputEmail1">Room id</label>
            <input type="text" className="form-control" required name="id_room" placeholder="id room"/>
        </div>
        <div className="form-group">
            <label for="exampleInputEmail1">Conference date</label>
            <input type="date" className="form-control" required name="datee" placeholder="date"/>
        </div>
        <div className="form-group">
            <label for="exampleInputEmail1">Conference time</label>
            <input type="time" className="form-control"v required name="timee" placeholder="time"/>
        </div>
        <div className="form-group">
            <label for="exampleInputEmail1">Your password</label>
            <input type="text" className="form-control" required name="admin_password" placeholder="your password"/>
        </div>
        <button className="btn btn-primary">Create new Conference</button>
    </form>
   </div>
)
            
export default FormRemoveParticipant;