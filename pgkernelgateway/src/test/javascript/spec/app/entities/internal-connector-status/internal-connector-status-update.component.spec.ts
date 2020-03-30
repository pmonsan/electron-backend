/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { InternalConnectorStatusUpdateComponent } from 'app/entities/internal-connector-status/internal-connector-status-update.component';
import { InternalConnectorStatusService } from 'app/entities/internal-connector-status/internal-connector-status.service';
import { InternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';

describe('Component Tests', () => {
  describe('InternalConnectorStatus Management Update Component', () => {
    let comp: InternalConnectorStatusUpdateComponent;
    let fixture: ComponentFixture<InternalConnectorStatusUpdateComponent>;
    let service: InternalConnectorStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [InternalConnectorStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InternalConnectorStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InternalConnectorStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternalConnectorStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InternalConnectorStatus(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new InternalConnectorStatus();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
