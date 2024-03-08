interface PersonDetailsProps {
  details: any;
  imageWidth?: number;
  imageHeight?: number;
  contentPadding?: number;
}

export default function PersonDetails({
  details,
  imageWidth,
  imageHeight,
  contentPadding,
}: PersonDetailsProps) {
  const hideDetails = details.name == undefined;

  return (
    <div
      className="grid"
      style={hideDetails ? { display: "none" } : { marginLeft: contentPadding }}
    >
      <div className="row">
        <div className="col-4">
          <img
            src={details.imageUrl}
            style={{ maxWidth: imageWidth, maxHeight: imageHeight }}
            alt={"Image of " + details.name}
            title={"Image of " + details.name}
          ></img>
        </div>
        <div
          className="col-8"
        >
          <div className="grid">
            <div className="row">
              <div className="col-3">
                <span>Name:</span>
              </div>
              <div className="col-6">
                <strong>{details.name}</strong>
              </div>
            </div>
            <div className="row">
              <div className="col-3">
                <span>Role:</span>
              </div>
              <div className="col-8">
                <strong>{details.role}</strong>
              </div>
            </div>
            <div className="row">
              <div className="col-3">
                <span>Age:</span>
              </div>
              <div className="col-6">
                <strong>{getAgeToDisplay(details.age)}</strong>
              </div>
            </div>
            <div className="row">
              <div className="col-3">
                <span>Bio:</span>
              </div>
              <div className="col-6">
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
          </div>
        </div>
      </div>
    </div>
  );
}

function getAgeToDisplay(age: number): string {
  if (age === -1) {
    return "?";
  }

  return "" + age;
}
