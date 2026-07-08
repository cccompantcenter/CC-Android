# CC-011 – Views & Contexts

## 1. Purpose
This document defines how Command Center presents the same Universal Cards across different views.

Views are not separate data models.
Views help the user work with the same Cards in the right context, at the right moment.

The goal is contextual clarity without fragmenting data.

## 2. Core principle
- One Universal Card model.
- Multiple contextual views.
- No duplicated task, project, agenda, contact, or archive systems.

This ensures consistency across navigation, storage, and interaction behavior.

## 3. Main views
Purpose of each main view:

- Vandaag: daily focus surface for current priorities and time-relevant Cards.
- Gedachten Inbox: intake queue for newly captured, unprocessed Gedachten.
- Cards: neutral all-Cards overview for broad browsing and management.
- Projecten: context view for Cards linked to project outcomes.
- Agenda: time-based view for Cards linked to dates, times, or calendar items.
- Contacten: relationship view for Cards linked to specific contacts.
- Wachten op: follow-up view for Cards in waiting state or with waiting metadata.
- Archief: preserved history view for archived Cards and long-term traceability.
- Settings: configuration view for behavior, preferences, and integrations.

## 4. View filtering
Cards and Gedachten appear in views based on properties, status, and relationships.

Examples:
- Vandaag shows Cards relevant today.
- Projecten shows Cards linked to a project.
- Agenda shows Cards with date/time information.
- Contacten shows Cards linked to a contact.
- Wachten op shows Cards with waiting status or waitingFor metadata.
- Archief shows archived Cards.
- Gedachten Inbox shows unprocessed Gedachten.

Filtering rules:
- Views evaluate existing fields and links.
- Views do not create new base entities.
- A change in Card metadata can change view membership immediately.

## 5. No duplication rule
- A Card may appear in multiple views.
- The Card is not copied.
- Changes to a Card are reflected everywhere.
- Views only filter, group, or present existing Cards.

This rule preserves one source of operational truth for Card data.

## 6. Navigation between views
Users should move between views through linked context while preserving identity continuity.

Required navigation paths:
- From Vandaag to Card detail.
- From Project to related Cards.
- From Agenda item to Card.
- From Contact to linked Cards.
- From Gedachte to processed Card.
- From Archive to preserved Card history.

Navigation behavior:
- Transitions should keep the same Card identity.
- Back navigation should remain predictable.
- Linked context should be visible in Card detail.

## 7. S Pen-first interaction
- Views must support quick handwritten capture.
- The user should be able to create a new Gedachte from every main view.
- Editing remains S Pen-first where possible.

Interaction principle:
- Handwriting capture should be fast, low-friction, and always available from primary surfaces.

## 8. AI support in views
- AI may help suggest relevant Cards.
- AI may suggest grouping, priority, or context.
- AI must not silently move or duplicate Cards.
- User confirmation remains required.

AI suggestions must remain transparent, reviewable, and reversible.

## 9. Future implementation guidance
- Future screens should be implemented as views over the Universal Card model.
- Do not introduce separate isolated task, project, agenda, or note data flows.
- Navigation, storage, and AI must respect this view architecture.

Implementation detail may evolve, but view behavior must always map back to the same Universal Card and domain model contracts.
