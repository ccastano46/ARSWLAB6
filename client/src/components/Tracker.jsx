import React from "react";
import PropTypes from 'prop-types';
import '../styles/Tracker.css';


export function TrackerElement({type,id,title,placeHolder}){
    return(
        <div className="trackerElement">
            <label htmlFor={id}>{title}:</label>
            <input type={type} id={id} placeholder={placeHolder}/>
        </div>
    );
}

TrackerElement.propTypes = {
    type: PropTypes.string.isRequired,
    id : PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    placeHolder: PropTypes.string
};


function Tracker({children, buttonName,handleClick}) {
    const onSubmit = (e) => {
        e.preventDefault();
        handleClick();
    };
    return (
        <form className="tracker" onSubmit={onSubmit}>
            {children}
            <input type="submit" value={buttonName} className={"submit"}></input>
        </form>
    );
}

Tracker.propTypes = {
    children: PropTypes.node,
    buttonName: PropTypes.string.isRequired,
    handleClick: PropTypes.func.isRequired
};



export default Tracker