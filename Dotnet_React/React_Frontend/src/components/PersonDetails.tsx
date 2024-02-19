interface PersonDetailsProps {
  details: any;
  className?: string;
  imageSize: number;
}

export default function PersonDetails({
  details,
  className,
  imageSize,
}: PersonDetailsProps) {
  if (details == undefined) {
    return (
      <div className={className}>
        <p>Please select person</p>
      </div>
    );
  }

  const usedClassName =
    "grid" +
    (className == undefined || className.length === 0 ? "" : " " + className);

  return (
    <div className={usedClassName}>
      <div className="row">
        <div className="col-2">
          <img
            src={details.imageUrl}
            width={imageSize}
            alt={"Image of " + details.name}
            title={"Image of " + details.name}
          ></img>
        </div>
        <div className="col-6">
          <div className="grid">
            <div className="row">
              <div className="col-2">
                <span>Name:</span>
              </div>
              <div className="col">
                <strong>{details.name}</strong>
              </div>
            </div>
            <div className="row">
              <div className="col-2">
                <span>Role:</span>
              </div>
              <div className="col">
                <strong>{details.role}</strong>
              </div>
            </div>
            <div className="row">
              <div className="col-2">
                <span>Age:</span>
              </div>
              <div className="col">
                <strong>{getAgeToDisplay(details.age)}</strong>
              </div>
            </div>
            <div className="row">
              <div className="col-2">
                <span>Bio:</span>
              </div>
              <div className="col">
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
    return "? ? ?";
  }

  return "" + age;
}
