# CC-004 – Data Storage Architecture

## 1. Purpose
This document defines the storage architecture for Command Center and its role in preserving domain integrity over time.

Data storage must:
- preserve the original Gedachte permanently,
- maintain reliable links between domain entities,
- support future growth without changing the domain model.

The storage layer is an implementation of the domain, not the owner of the domain definition.

## 2. Storage principles
The storage architecture follows these principles:

- Single source of truth: each domain fact has one canonical record.
- Local-first architecture: primary read/write happens on-device.
- Offline-first capability: core workflows remain functional without network.
- Reliable synchronization: sync reconciles state across devices without breaking source integrity.
- Data integrity: constraints and validation prevent inconsistent states.
- Future cloud compatibility: architecture supports cloud sync extensions without redesigning core entities.

## 3. Core data entities
The following entities are persisted as first-class records with stable identifiers:

- Gedachte: immutable original capture record, including source metadata and handwritten payload references.
- Card: central working object derived from or linked to Gedachte, with mutable lifecycle fields.
- Project: structured grouping context linked from Cards.
- Action: executable unit linked from Cards and optionally grouped under Project.
- Calendar item: time-based scheduling entity linked from Cards.
- Contact: person/entity reference linked from Cards.
- Destination: routing classification that provides context for Card placement and behavior.
- Archive: preserved state and retention context for inactive/completed records.

Entity persistence should preserve clear ownership boundaries and avoid implicit duplication across modules.

## 4. Relationships
Relationships are stored via references, not by duplicating full object payloads.

Examples:
- Gedachte -> Card
- Card -> Project
- Card -> Calendar item
- Card -> Contact
- Card -> Linked Cards
- Card -> Attachments

Relationship rules:
- Use stable IDs for links.
- Keep relation metadata (for example role, createdAt, ordering) in join/association structures where needed.
- Preserve directional meaning where domain semantics require it.
- Avoid copying title/description/content between entities solely for convenience.

This keeps data consistent, reduces drift, and simplifies synchronization.

## 5. Versioning
Storage must support controlled evolution of mutable entities while preserving immutable source content.

Versioning and history rules:
- Original Gedachte never changes.
- Card evolves over time through updates.
- Modification history is preserved for relevant mutable entities.
- Audit trail records who/what changed data and when (including system/AI-assisted actions where applicable).
- Deleted versus archived objects are distinct states:
  - Archived: intentionally retained and recoverable.
  - Deleted: removed from active domain use according to retention policy.

Versioning should allow traceability from current Card state back to originating Gedachte.

## 6. Attachments
Attachment storage must preserve originals and maintain explicit linkage to owning entities.

Supported attachment categories:
- Handwritten pages
- Images
- Documents
- Audio
- Future OCR results
- Future AI summaries

Attachment principles:
- Store attachment metadata separately from binary payload when possible.
- Keep immutable source assets intact.
- Attachments remain linked to Card or Gedachte and never replace original source content.
- Derived outputs (OCR/AI summaries) are additive artifacts, not source substitutions.

## 7. Synchronization principles
Future synchronization architecture should extend local data ownership, not replace it.

Synchronization principles:
- Local device remains primary.
- Cloud synchronization is secondary.
- Conflict handling is deterministic and traceable.
- Unique identifiers are globally stable across devices.
- Device independence ensures the same data model works consistently across clients.

Provider neutrality requirement:
- Do not bind storage contracts to one cloud vendor.

## 8. Performance principles
Storage design must scale while keeping interaction responsive.

Performance principles:
- Fast startup through minimal critical-path loading.
- Efficient loading via targeted queries and bounded payloads.
- Lazy loading where appropriate for heavy attachments and secondary relations.
- Scalable architecture for growth in records, links, and media volume.
- Efficient search indexing across titles, metadata, and selected content artifacts.
- Minimal duplication to reduce storage cost and sync overhead.

## 9. Security principles
Storage and sync design must protect user data by default.

Security and privacy principles:
- Local encryption support for sensitive data at rest.
- Secure synchronization channels and authenticated transport.
- Privacy by design in schema, telemetry, and data access boundaries.
- User ownership of data as a non-negotiable product rule.
- AI only receives data when explicitly required by user action or approved workflow.

No implicit external sharing should occur from storage events alone.

## 10. Future implementation guidance
Future persistence technologies (for example Room or another database) are implementation choices.

They must follow this storage architecture and domain constraints rather than define them.

Guidance for future technical decisions:
- Keep domain contracts stable.
- Map technology-specific schemas to domain entities without changing domain semantics.
- Preserve Gedachte immutability and Card-centered relationship structure.
- Ensure migration strategies maintain integrity, traceability, and backward compatibility.

This architecture is the governing model; persistence tooling is replaceable.
