import React from "react";
import styled, { keyframes } from "styled-components";

const rotate = keyframes`
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
`;

const LoadingContainer = styled.div`
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
`;

const LoadingSpinner = styled.div`
    width: 5rem;
    height: 5rem;
    border: 0.5rem solid var(--quinary);
    border-top: 0.5rem solid var(--secondary);
    border-radius: 50%;
    animation: ${rotate} 1.5s linear infinite;
`;

export default function LoadingPage() {
    return (
        <LoadingContainer>
            <LoadingSpinner />
        </LoadingContainer>
    );
}
