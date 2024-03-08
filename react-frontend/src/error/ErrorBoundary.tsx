import React from "react";
import ErrorPage from "./ErrorPage";

type ErrorBoundaryProps = {
    children: React.ReactNode;
};

type ErrorBoundaryState = {
    hasError: boolean;
    error: Error | null;
};

class ErrorBoundary extends React.Component<ErrorBoundaryProps, ErrorBoundaryState> {
    constructor(props: ErrorBoundaryProps) {
        super(props);
        this.state = { hasError: false, error: null };
    }

    static getDerivedStateFromError(error: Error) {
        return { hasError: true, error };
    }

    render() {
        const { hasError, error } = this.state;
        const { children } = this.props;

        if (hasError) {
            const statusCode = error instanceof Response ? error.status : 500;
            const errorMessage = error
                ? error instanceof Response
                    ? error.statusText
                    : error.message
                : "Unknown error occurred";
            return <ErrorPage statusCode={statusCode} errorMessage={errorMessage} />;
        }

        return children;
    }
}

export default ErrorBoundary;
