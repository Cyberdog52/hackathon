import { useEffect, useState } from "react";
import { fetchRandomJoke } from "./JokeApi";

interface JokeViewProps {
  apiUrl: string;
  viewMargin: number;
  contentPadding: number;
}

export default function JokeView({
  apiUrl,
  viewMargin,
  contentPadding,
}: JokeViewProps) {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [joke, setJoke] = useState<any>([]);

  useEffect(() => {
    getNewJoke();
  }, []);

  function getNewJoke() {
    setIsLoading(true);
    fetchRandomJoke(apiUrl, (x) => {
      console.log("New joke: ", x);
      setJoke(x);
      setIsLoading(false);
    });
  }

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
        <span>Get another joke&nbsp;&nbsp;&nbsp;</span>
        {isLoading ? (
          <div
            className="spinner-grow spinner-grow-sm"
            role="status"
            style={{}}
          ></div>
        ) : null}
      </button>
    </div>
  );
}
