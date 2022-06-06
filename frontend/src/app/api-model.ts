export interface CveMention {
    id: string;
    tweetIds: [number];
    mentionCount: number;
}

export interface CvePoc {
    repoId: number;
    repoName: string;
    dateCreated: Date;
    dateUpdated: Date;
    stars: number;
    url: string;
}

export interface Vulnerability {
    vulnerabilityId: string;
    vulnerabilityTitle: string;
    vulnerabilityCwe: string;
    vulnerabilityDatePublished: Date;
    vulnerabilityDateUpdated: Date;
    vulnerabilityCpe: string;
    cvssBaseScore: number;
    priority: number;
  }