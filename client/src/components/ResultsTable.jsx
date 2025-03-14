import React, {useEffect, useState, useRef} from "react";
import PropTypes from 'prop-types';
import '../styles/ResultsTable.css';


function ResultsTable({title,headers, search, attributes,children}) {
    const [infoList, setInfoList] = useState([]);
    const trRefs = useRef([]);
    useEffect(() => {
        const searchBlueprints = async () => {
            try {
                const response = await fetch(search);
                if (!response.ok) throw new Error('Failed to fetch blueprints');
                const data = await response.json();
                setInfoList([...data]);
            } catch (error) {
                console.error('Error fetching blueprints', error, {search});
            }
        };
        searchBlueprints();
    }, [search]);


    if (infoList.length === 0) {
        return (<h2>No blueprints associate to {title}</h2>
        );

    }

    return (
        <table className={"results"}>
            <caption>{title}</caption>
            <thead>
                <tr>
                    {headers.map((header, index) => (
                        <th key={index}>{header}</th>
                    ))}
                </tr>
            </thead>
            <tbody>
                {infoList.map((element, index) => (
                    <tr key={index} id={index} ref={(el) => (trRefs.current[index] = el)}>
                        {attributes.map((attr, attrIndex) => (
                            <td key={attrIndex}>{element[attr]}</td>
                        ))}
                        {React.Children.map(children, child =>
                            React.cloneElement(child, {
                                trRef: trRefs.current[index],
                                row: index
                            })
                        )}
                    </tr>
                ))}
            </tbody>

        </table>
    );
}

ResultsTable.propTypes = {
    title: PropTypes.string.isRequired,
    headers: PropTypes.arrayOf(PropTypes.string).isRequired,
    search: PropTypes.string.isRequired,
    attributes: PropTypes.arrayOf(PropTypes.string).isRequired,
    children: PropTypes.node
};

export function ExtraColumn({children, trRef,row}) {
    return (
        <td>
            {typeof children === 'function'
                ? children(trRef,row)
                : children
            }
        </td>
    );
}

ExtraColumn.propTypes = {
    children: PropTypes.oneOfType([
        PropTypes.node,
        PropTypes.func
    ]),
    trRef: PropTypes.oneOfType([
        PropTypes.shape({
            current: PropTypes.instanceOf(Element)
        })
    ]),
    row: PropTypes.number
};
export default ResultsTable