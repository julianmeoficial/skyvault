export interface NewsArticle {
    id: string;
    title: string;
    content: string;
    category: 'Boeing' | 'Airbus' | 'Aerolíneas' | 'Infraestructura';
    imageUrl: string;
    imageUrl2?: string;
    imageUrl3?: string;
    date: string;
    isFeatured?: boolean;
    tags?: string[];
}

export type NewsCategory = 'Todas' | 'Boeing' | 'Airbus' | 'Aerolíneas' | 'Infraestructura';
