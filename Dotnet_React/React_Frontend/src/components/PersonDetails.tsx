interface PersonDetailsProps {
  details: any;
}

function PersonDetails({ details }: PersonDetailsProps) {
  
  if(details == undefined)
  {
    return <></>
  }
    
  return (
    <div className="container">
      <div className="row">
        <div className="col-4 p-3">
          <span>Name:</span>
        </div>
        <div className="col-6 p-3">
          <strong>{details.name}</strong>
        </div>
      </div>
      <div className="row">
        <div className="col-4 p-3">
          <span>Role:</span>
        </div>
        <div className="col-6 p-3">
          <strong>{details.role}</strong>
        </div>
      </div>
      <div className="row">
        <div className="col-4 p-3">
          <span>Age:</span>
        </div>
        <div className="col-6 p-3">
          <strong>{details.age}</strong>
        </div>
      </div>
      <div className="row">
        <div className="col-4 p-3">
          <span>Bio:</span>
        </div>
        <div className="col-6 p-3">
          <strong>
            <a
              href={details.bioUrl}
              target="_blank"
              title={"Link to the biography of " + details.name}
            >
              Biography
            </a>
          </strong>
        </div>
      </div>
      <div className="row ">
        <div className="col-0 p-3">
          <span></span>
        </div>
        <div className="col-0 p-3">
          <img
            src={details.imageUrl}
            width="100"
            alt={"Image of " + details.name}
            title={"Image of " + details.name}
          ></img>
        </div>
      </div>
    </div>
  );
}

export default PersonDetails;