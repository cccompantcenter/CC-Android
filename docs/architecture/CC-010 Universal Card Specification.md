# CC-010 – Universal Card Specification

## 1. Purpose
The Universal Card is the central working object of Command Center.

A Card represents meaningful information derived from or linked to a Gedachte.

The Card does not replace the original Gedachte.
The original Gedachte remains preserved as source of truth.

## 2. Core rule
There is one Card model.

Task, project, note, meeting, contact, waiting-for, and archive are not separate base models.
They are contexts, types, or views of the same Universal Card.

This rule prevents duplicate data structures and keeps all workflows aligned to one domain object.

## 3. Required Card fields
Every Universal Card must include the following required fields:

- id
- sourceGedachteId
- title
- description
- type
- status
- priority
- createdAt
- updatedAt
- archivedAt
- deletedAt
- tags
- attachments
- handwrittenContent
- linkedCardIds
- projectId
- calendarItemId
- contactId
- destination

Field intent guidance:
- Identity and traceability are anchored through id and sourceGedachteId.
- Lifecycle control is managed through status and time markers.
- Context linkage is managed through projectId, calendarItemId, contactId, linkedCardIds, and destination.
- Human-authored source expression is preserved in handwrittenContent.

## 4. Optional Card extensions
The Universal Card may include optional extensions for richer workflows:

- dueDate
- startDate
- endDate
- reminder
- waitingFor
- checklist
- aiSummary
- aiSuggestedTitle
- aiSuggestedTags
- ocrText
- notes
- location

Extensions are additive and must not change the core Card contract.

## 5. Card lifecycle
The Card lifecycle follows this sequence:

Gedachte -> Processing -> Card created -> Active -> Linked to context -> Completed or archived -> Preserved in archive

Lifecycle interpretation:
- Gedachte is captured as original source.
- Processing interprets and structures without mutating source.
- Card created marks transition to a structured working object.
- Active indicates current execution relevance.
- Linked to context connects Card to Project, Agenda, Contact, Waiting, or Archive views.
- Completed or archived closes active execution.
- Preserved in archive ensures history and retrievability.

## 6. Status model
The Universal Card status model is:

- Draft
- Active
- Waiting
- Completed
- Archived
- Deleted

Status principles:
- Status transitions should remain explicit and auditable.
- Archived and Deleted are distinct outcomes.
- Source linkage to Gedachte remains intact in all statuses.

## 7. View behavior
The same Universal Card can appear in:
- Vandaag
- Projecten
- Agenda
- Contacten
- Wachten op
- Archief

It appears in these views through filtering, context links, and metadata interpretation, not by duplicating Card records.

View rules:
- One Card record can be rendered in multiple views simultaneously when relevant.
- Updates in one view are reflected everywhere the Card appears.
- Navigation between views must preserve the same Card identity.

## 8. S Pen-first behavior
- Handwritten input is primary.
- Keyboard input is secondary.
- Every Card must support handwritten notes or handwritten edits.

S Pen-first is a domain requirement and applies to both creation and editing workflows.

## 9. AI behavior
- AI may enrich a Card.
- AI may suggest title, summary, tags, priority, or destination.
- AI must not overwrite the source Gedachte.
- AI suggestions require user confirmation.

AI outputs are proposal artifacts and must remain traceable, reviewable, and reversible.

## 10. Future implementation guidance
Future modules must extend or reference the Universal Card.

Do not introduce separate isolated task, project, note, or agenda models.

Storage, UI, and AI layers must all follow this Card specification.

Implementation technologies may evolve, but this specification remains the governing architecture contract.
