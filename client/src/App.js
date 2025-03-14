import React, {useEffect, useState} from 'react';
import './styles/App.css';
import Tracker, {TrackerElement} from "./components/Tracker";
import ResultsTable, {ExtraColumn} from "./components/ResultsTable";
import Canvas from "./components/Canvas";

function App() {
    const [showResults, setShowResults] = useState(false);
    const[author,setAuthor] = useState('')
    const [showBp, setShowBp] = useState(false);
    const [bp, setBp] = useState(null);


    const searchCLick = () => {
        setShowResults(false);
        setAuthor(document.getElementById('author').value);
        setShowResults(true);
    };


    useEffect(() => {
        if (bp !== null) {
            setShowBp(true);
        } else{
            setShowBp(false);
        }
        console.log(bp, showBp)
    }, [bp]);




    useEffect(() => {
        if (author) {
            setShowResults(true);
            setBp(null);
        }
    }, [author]);

  return (
      <>
          <Tracker buttonName={"Get Blueprints"} handleClick={searchCLick}>
              <TrackerElement type={"text"} id={"author"} title={"Author"} placeHolder={"Author's Name"}></TrackerElement>
          </Tracker>
          {showResults && (
              <div className={"results"}>
                  <ResultsTable headers={["Name", "Number of Points", " "]}
                                search={'http://localhost:8080/blueprints/' + author}
                                attributes={['name', 'numberPoints']} title={author}>
                      <ExtraColumn>
                          {(trRef,row) => (

                              <button
                                  onClick={() => {
                                      const firstColumnValue = trRef.querySelector('td').textContent;
                                      setBp(firstColumnValue);
                                  }}
                              >Open</button>
                          )}
                      </ExtraColumn>
                  </ResultsTable>
                  {showBp && (
                      <Canvas blueprint={bp} server={'http://localhost:8080/blueprints/' + author + '/'}/>
                  )}

              </div>
          )}
      </>


  );
}

export default App;
