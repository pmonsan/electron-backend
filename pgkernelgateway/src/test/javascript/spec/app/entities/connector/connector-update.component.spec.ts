/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ConnectorUpdateComponent } from 'app/entities/connector/connector-update.component';
import { ConnectorService } from 'app/entities/connector/connector.service';
import { Connector } from 'app/shared/model/connector.model';

describe('Component Tests', () => {
  describe('Connector Management Update Component', () => {
    let comp: ConnectorUpdateComponent;
    let fixture: ComponentFixture<ConnectorUpdateComponent>;
    let service: ConnectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ConnectorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ConnectorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnectorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnectorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Connector(123);
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
        const entity = new Connector();
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
