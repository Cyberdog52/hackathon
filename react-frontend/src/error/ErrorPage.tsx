import React from "react";
import styled from "styled-components";

const ErrorTitle = styled.h1`
    font-size: 1.5em;
    text-align: center;
    color: black;
`;

const ErrorParagraph = styled.p`
    text-align: center;
`;

type ErrorPageProps = {
    statusCode: number;
    errorMessage: string;
};

const ErrorPage: React.FC<ErrorPageProps> = ({ statusCode, errorMessage }) => {
    return (
        <div id="error-page">
            <ErrorTitle>Oops!</ErrorTitle>
            <ErrorParagraph>Sorry, an unexpected error has occurred.</ErrorParagraph>
            <ErrorParagraph>
                {statusCode} - {errorMessage}
            </ErrorParagraph>
        </div>
    );
};

export default ErrorPage;
