import { useEffect, useState } from "react";
import { fetchRandomJoke } from "./JokeApi";

interface JokeViewProps {
  apiUrl: string;
  viewMargin?: number;
  contentPadding?: number;
}

export default function JokeView({
  apiUrl,
  viewMargin,
  contentPadding,
}: JokeViewProps) {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [hasFirstLoaded, setHsFirstLoaded] = useState<boolean>(false);
  const [joke, setJoke] = useState<any>([]);

  function getNewJoke() {
    setIsLoading(true);
    if(isLoading)
    {
      return;
    }
    fetchRandomJoke(apiUrl, (x) => {
      console.log("New joke: ", x);
      setJoke(x);
      setIsLoading(false);
      setHsFirstLoaded(true);
    });
  }

  const buttonText = hasFirstLoaded ? "Get another joke" : "Get a joke";

  return (
    <div style={{ margin: viewMargin }}>
      <p className="fs-5">
        {joke.setup}
        <br />
        {joke.punchline}
      </p>
      <button
        className="btn btn-dark"
        style={{ marginTop: contentPadding }}
        onClick={getNewJoke}
      >
        <span>{buttonText}&nbsp;&nbsp;&nbsp;</span>
        {isLoading ? (
          <div className="spinner-grow spinner-grow-sm" role="status"></div>
        ) : null}
      </button>
    </div>
  );
}
