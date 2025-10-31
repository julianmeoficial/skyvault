export interface LoaderProps {
    phase?: LoadingPhase;
    text?: string;
    onComplete?: () => void;
}

export type LoadingPhase = 'dots' | 'letters' | 'hidden';

export interface LoaderAnimationRefs {
    dotsRef: React.RefObject<HTMLDivElement>;
    letterLoaderRef: React.RefObject<HTMLDivElement>;
}
