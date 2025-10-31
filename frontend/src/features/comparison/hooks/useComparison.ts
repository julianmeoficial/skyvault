import { useState, useEffect, useCallback, useMemo } from 'react';
import type { ComparisonItem, ComparisonState } from '../types/comparison.types';
import { comparisonService } from '../services/comparisonService';

const MAX_COMPARISON_ITEMS = 3;

export const useComparison = (initialIds: string[] = []) => {
    const [state, setState] = useState<ComparisonState>({
        items: [],
        maxItems: MAX_COMPARISON_ITEMS,
        isLoading: false,
        error: null,
    });

    // Estabilizar el array de IDs con useMemo
    const stableIds = useMemo(() => initialIds.join(','), [initialIds.join(',')]);

    // Cargar datos de comparación
    const loadComparisonData = useCallback(async (ids: string[]) => {
        if (ids.length === 0) {
            setState(prev => ({ ...prev, items: [], isLoading: false }));
            return;
        }

        setState(prev => ({ ...prev, isLoading: true, error: null }));

        try {
            const data = await comparisonService.fetchComparisonData(ids);
            setState(prev => ({ ...prev, items: data, isLoading: false }));
        } catch (error) {
            setState(prev => ({
                ...prev,
                isLoading: false,
                error: error instanceof Error ? error.message : 'Error loading comparison data',
            }));
        }
    }, []);

    // Agregar item a la comparación
    const addItem = useCallback(
        (item: ComparisonItem) => {
            setState(prev => {
                if (prev.items.length >= MAX_COMPARISON_ITEMS) {
                    return {
                        ...prev,
                        error: `Máximo ${MAX_COMPARISON_ITEMS} aeronaves para comparar`,
                    };
                }

                const exists = prev.items.some(i => i.id === item.id);
                if (exists) {
                    return { ...prev, error: 'Esta aeronave ya está en la comparación' };
                }

                return {
                    ...prev,
                    items: [...prev.items, item],
                    error: null,
                };
            });
        },
        []
    );

    // Remover item de la comparación
    const removeItem = useCallback((id: number) => {
        setState(prev => ({
            ...prev,
            items: prev.items.filter(item => item.id !== id),
            error: null,
        }));
    }, []);

    // Limpiar comparación
    const clearAll = useCallback(() => {
        setState(prev => ({
            ...prev,
            items: [],
            error: null,
        }));
    }, []);

    // Cargar datos iniciales - ✅ CORREGIDO: usar stableIds
    useEffect(() => {
        const ids = stableIds.split(',').filter(id => id.length > 0);
        if (ids.length > 0) {
            loadComparisonData(ids);
        }
    }, [stableIds, loadComparisonData]);

    return {
        items: state.items,
        maxItems: state.maxItems,
        isLoading: state.isLoading,
        error: state.error,
        addItem,
        removeItem,
        clearAll,
        canAddMore: state.items.length < MAX_COMPARISON_ITEMS,
    };
};
