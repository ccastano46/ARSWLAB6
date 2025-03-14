import React, {useState,useEffect} from "react";
import PropTypes from 'prop-types';
import '../styles/Canvas.css'



function Canvas({server,blueprint}) {
    const [blueprintsPoints, setBlueprintsPoints] = useState([]);
    useEffect(() => {
        const searchPoints = () => {
            fetch(server + blueprint)
                .then(response => {
                    if (!response.ok) throw new Error('Failed to fetch blueprint');
                    return response.json();
                })
                .then(bp => {
                    const {points} = bp;
                    setBlueprintsPoints(points);
                })
                .catch(error => {
                    console.error('Error fetching blueprints', error, {blueprint});
                });
        };
        searchPoints();
    },[blueprintsPoints]);

    if(blueprintsPoints.length === 0) return null;

    const pointsString= blueprintsPoints.map(point => `${point.x},${point.y}`).join(' ');
    const minX = Math.min(...blueprintsPoints.map(point => point.x));
    const minY = Math.min(...blueprintsPoints.map(point => point.y));
    const maxX = Math.max(...blueprintsPoints.map(point => point.x));
    const maxY = Math.max(...blueprintsPoints.map(point => point.y));
    const viewBox = `${minX} ${minY} ${maxX - minX} ${maxY - minY}`;

    return (
        <div className={"canvas"}>
            <h2>{blueprint}</h2>
            <svg viewBox={viewBox} preserveAspectRatio="xMidYMid meet">
                <polygon points={pointsString}/>
            </svg>
        </div>
    );
}

Canvas.propTypes ={
    server: PropTypes.string.isRequired,
    blueprint: PropTypes.string.isRequired
};




export default Canvas